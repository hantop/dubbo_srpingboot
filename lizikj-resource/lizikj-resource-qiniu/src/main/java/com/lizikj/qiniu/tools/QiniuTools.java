package com.lizikj.qiniu.tools;


import com.google.gson.Gson;
import com.lizikj.qiniu.KEY.Qiniu;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QiniuTools {

    private static final Log log= LogFactory.getLog(QiniuTools.class);

    private static String accessKey= Qiniu.accessKey;

    private static String secretKey= Qiniu.secretKey;

    private static String bucket= Qiniu.bucket;

    private static String domain= Qiniu.bindDomain;

    public static String getAccessKey() {
        return accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static String getBucket() {
        return bucket;
    }

    public static String getDomain() {
        return domain;
    }

    /**
     * 创建空间管理控件
     * @return
     */
    private static BucketManager createBucketManager(){
        Configuration cfg = new Configuration(Zone.zone2());
        Auth auth = Auth.create(accessKey, secretKey);
        return new BucketManager(auth, cfg);
    }
    /**
     * 创建空间管理控件
     * @return
     */
    private static BucketManager createBucketManager(String accessKey,String secretKey){
        Configuration cfg = new Configuration(Zone.zone2());
        Auth auth = Auth.create(accessKey, secretKey);
        return new BucketManager(auth, cfg);
    }
    /**
     * 创建上传管理空间
     * @return
     */
    public static UploadManager createUploadManager(){
        Configuration cfg = new Configuration(Zone.zone2());
        return new UploadManager(cfg);
    }
    /**
     * 获取上传凭证
     * @return
     */
    public static String gainUploadVoucher(String accessKey,String secretKey,String bucket){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }
    /**
     * 获取上传凭证
     * @return
     */
    public static String gainUploadVoucher(){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }
    /**
     * 获取上传凭证
     * @return
     */
    public static String gainUploadVoucher(String accessKey,String secretKey,String bucket,String fileName){
        Auth auth = Auth.create(accessKey, secretKey);
        return  auth.uploadToken(bucket, fileName);
    }
    /**
     * 获取上传凭证
     * @return
     */
    public static String gainUploadVoucher(String fileName){
        Auth auth = Auth.create(accessKey, secretKey);
        return  auth.uploadToken(bucket, fileName);
    }

    private static void outResponse(DefaultPutRet putRet){
        if(null!=putRet){
            log.info("返回值为:"+Json.encode(putRet));
        }
    }

    /**
     * 根据键获取文件
     * @param key
     * @return
     */
    public static FileInfo findByKey(String key){
        BucketManager bucketManager = createBucketManager();
        try {
            log.info("连接七牛云开始获取文件信息，键值为："+key);
            FileInfo fileInfo = bucketManager.stat(bucket, key);
            if(null!=fileInfo){
                log.info("连接七牛云成功获取到文件信息："+Json.encode(fileInfo));
                return fileInfo;
            }
            log.warn("连接七牛云没有此文件,键值为："+key);
        } catch (QiniuException e) {
            outQiniuException(e);
        }
        return null;
    }

    public static void upload(File file,String key){
        String filePath=file.getPath();
        upload(filePath,key);
    }

    public static void upload(String filePath,String key){
        UploadManager uploadManager = createUploadManager();
        String upToken = gainUploadVoucher();
        try {
            log.info("连接七牛云开始上传文件，文件路径为："+filePath);
            Response response = uploadManager.put(filePath, key, upToken);
            log.info("连接七牛云文件上传成功，文件路径为："+filePath);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            outResponse(putRet);//需作数据存储处理
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    public static void update(byte[] bytes){
        UploadManager uploadManager = createUploadManager();
        String upToken = gainUploadVoucher();
        try {
            log.info("连接七牛云开始上传字节数组-");
            Response response = uploadManager.put(bytes, null, upToken);
            log.info("连接七牛云字节数组上传成功*");
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            outResponse(putRet);
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    public static void upload(InputStream inputStream){
        UploadManager uploadManager = createUploadManager();
        String upToken = gainUploadVoucher();
        try {
            log.info("连接七牛云开始上传输入流");
            Response response = uploadManager.put(inputStream,null,upToken,null, null);
            log.info("连接七牛云成功上传输入流");
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            outResponse(putRet);
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }
    private static void outQiniuException(QiniuException e){
        log.error("连接七牛平台操作失败！");
        log.error(e.getMessage(),e);
        log.error(""+Json.encode(e.response));
        try {
            log.error(e.response.bodyString());
        } catch (QiniuException ex2) {
            log.error(ex2.getMessage(),ex2);
        }
    }
    //断点续传
    public static void uploadMulti(String filePath){
        String upToken = gainUploadVoucher();
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        try {
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            Configuration cfg = new Configuration(Zone.zone0());
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                log.info("连接七牛云断点续传文件开始------");
                log.info("文件路径为："+filePath);
                Response response = uploadManager.put(filePath, null, upToken);
                log.info("连接七牛云断点续传文件结束......");
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                outResponse(putRet);
            } catch (QiniuException ex) {
                outQiniuException(ex);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }

    }

    /**
     * 更改文件类型
     * @param key
     * @param newType
     */
    public static void  update(String key,String newType){
        BucketManager bucketManager = createBucketManager();
        try {
            log.info("连接七牛云更新文件类型开始------");
            log.info("文件键值为："+key);
            log.info("文件新类型为："+newType);
            bucketManager.changeMime(bucket, key, newType);
            log.info("连接七牛云更新文件结束......");
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    /**
     * 操作支持强制覆盖选项，即如果目标文件已存在，可以设置强制覆盖参数force来覆盖那个文件的内容。
     * 移动操作本身支持移动文件到相同，不同空间中，在移动的同时也可以支持文件重命名。唯一的限制条件是，移动的源空间和目标空间必须在同一个机房。
     * @param bucketFrom
     * @param bucketTo
     * @param keyFrom
     * @param keyTo
     */
    public static void move(String bucketFrom,String bucketTo,String keyFrom,String keyTo){
        BucketManager bucketManager = createBucketManager();
        try {
            log.info("连接七牛云移动文件开始------");
            log.info("源空间为："+bucketFrom);
            log.info("源键值为："+keyFrom);
            log.info("目的空间为："+bucketTo);
            log.info("目的键值为："+keyTo);
            bucketManager.move(bucketFrom, keyFrom, bucketTo, keyTo);
            log.info("连接七牛云移动文件结束......");
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    /**
     * 文件的复制和文件移动其实操作一样，主要的区别是移动后源文件不存在了，而复制的结果是源文件还存在，只是多了一个新的文件副本。
     * @param bucketFrom
     * @param bucketTo
     * @param keyFrom
     * @param keyTo
     */
    public static void copy(String bucketFrom,String bucketTo,String keyFrom,String keyTo){
        BucketManager bucketManager = createBucketManager();
        try {
            log.info("连接七牛云复制文件开始------");
            log.info("源空间为："+bucketFrom);
            log.info("源键值为："+keyFrom);
            log.info("目的空间为："+bucketTo);
            log.info("目的键值为："+keyTo);
            bucketManager.copy(bucketFrom, keyFrom, bucketTo, keyTo);
            log.info("连接七牛云复制文件结束......");
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    /**
     * 删除
     * @param key
     */
    public static void delete(String key){
        BucketManager bucketManager = createBucketManager();
        try {
            log.info("连接七牛云删除文件开始,文件键值为："+key);
            bucketManager.delete(bucket, key);
            log.info("连接七牛云删除文件结束,文件键值为："+key);
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }

    }

    /**
     * 可以给已经存在于空间中的文件设置文件生存时间，
     * 或者更新已设置了生存时间但尚未被删除的文件的新的生存时间。
     * @param key
     * @param days
     */
    public static void expire(String key,int days){
        try {
            BucketManager bucketManager = createBucketManager();
            log.info("连接七牛云重置文件有效期开始------");
            log.info("文件键值为："+key);
            log.info("有效期为："+days);
            bucketManager.deleteAfterDays(bucket, key, days);
            log.info("连接七牛云重置文件有效期结束------");
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    /**
     * 查询空间文件
     * @param bucketName 空间名称
     * @param fileNamePreFix 文件名称前缀
     * @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
     * @param limit 数量
     * @return 条件查询的文件信息列表
     */
    public static List<FileInfo> query(String bucketName,String fileNamePreFix,String delimiter,int limit){
        BucketManager bucketManager = createBucketManager();
        if(limit==0)limit=1000;
        if(limit>1000){
            log.warn("每次迭代最大长度为1000，高于1000，按1000查询");
            limit = 1000;
        }
        List<FileInfo> fileInfos=new ArrayList<>();
        log.info("开始连接七牛云查询空间文件------");
        log.info("存储空间名称为："+bucketName);
        log.info("文件前缀为："+fileNamePreFix);
        log.info("目录分隔符为："+delimiter);
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucketName, fileNamePreFix, limit, delimiter);
        log.info("结束连接七牛云查询空间文件......");
        while (fileListIterator.hasNext()){
            FileInfo[] items = fileListIterator.next();
            if(null!=items&&items.length!=0){
                for (FileInfo item : items) {
                    fileInfos.add(item);
                }
            }
        }
        log.info("获取到满足条件的空间文件数量为："+fileInfos.size());
        if(!CollectionUtils.isEmpty(fileInfos))return fileInfos;
        return null;
    }

    /**
     * 抓取网络资源到空间
     */
    public static void grab(String fileKey,String remoteSrcUrl){
        BucketManager bucketManager = createBucketManager();
        try {
            log.info("开始抓取网络资源到七牛云，源地址为："+remoteSrcUrl);
            FetchRet fetchRet = bucketManager.fetch(remoteSrcUrl, bucket, fileKey);
            log.info("结束抓取网络资源到七牛云，源地址为："+remoteSrcUrl);
            if(null!=fetchRet){
                log.info("成功返回结果为："+Json.encode(fetchRet));
            }
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }

    /**
     * 批量获取文件信息
     */
    public static List<FileInfo> batchGet(String[] keyList){
        BucketManager bucketManager = createBucketManager();
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addStatOps(bucket, keyList);
            log.info("连接七牛云开始批量获取文件信息------");
            Response response = bucketManager.batch(batchOperations);
            log.info("连接七牛云结束批量获取文件信息......");
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            if(null!=batchStatusList&&batchStatusList.length!=0){
                List<FileInfo> fileInfos=new ArrayList<>();
                for (int i = 0; i < keyList.length; i++) {
                    BatchStatus status = batchStatusList[i];
                    String key = keyList[i];
                    log.info("读取文件信息:"+key);
                    if (status.code == 200) {
                        log.info("读取文件信息成功:"+key);
                        FileInfo fileInfo=new FileInfo();
                        fileInfo.hash=status.data.hash;
                        fileInfo.mimeType=status.data.mimeType;
                        fileInfo.fsize=status.data.fsize;
                        fileInfo.putTime=status.data.putTime;
                        fileInfos.add(fileInfo);
                    } else {
                        log.info("读取文件信息失败:"+key);
                        log.info("失败原因为:"+status.data.error);
                    }
                }
                if(CollectionUtils.isNotEmpty(fileInfos))
                    return fileInfos;
            }
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
        return null;

    }

    /**
     * 批量修改文件类型
     * @param keyMimeMap  键 ：文件键值;值 ：需要更改的类型
     */
    public static void batchUpdate(Map<String,String> keyMimeMap){
        BucketManager bucketManager = createBucketManager();
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            for (Map.Entry<String, String> po : keyMimeMap.entrySet()){
                batchOperations.addChgmOp(bucket, po.getKey(), po.getValue());
            }
            log.info("连接七牛云开始批量更改文件类型-----");
            Response response = bucketManager.batch(batchOperations);
            log.info("连接七牛云批量更改文件类型结束.....");
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            int index = 0;
            for (Map.Entry<String, String> entry : keyMimeMap.entrySet()) {
                String key = entry.getKey();
                BatchStatus status = batchStatusList[index];
                if (status.code == 200) {
                    log.info("批量更改成功，文件键值为："+key);
                } else {
                    log.info("批量更改失败，文件键值为："+key+"；失败原因为："+status.data.error);
                }
                index += 1;
            }
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }

    }

    /**
     * 批量删除
     * @param keyList 键集合
     */
    public static void batchDelete(String[] keyList){
        BucketManager bucketManager = createBucketManager();
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, keyList);
            log.info("连接七牛云开始批量删除------");
            Response response = bucketManager.batch(batchOperations);
            log.info("连接七牛云批量删除结束......");
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code == 200) {
                    log.info("批量删除成功，文件键值为："+key);
                } else {
                    log.info("批量删除失败，文件键值为："+key+";失败原因为："+status.data.error);
                }
            }
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }

    }

    /**
     * 批量移动或重命名文件
     * @param keyMap 旧键值映射新键值的map
     * @param bucketFrom 源空间
     * @param bucketTo 新空间
     */
    public static void batchMove(Map<String,String> keyMap,String bucketFrom,String bucketTo){
        if(null!=keyMap&&keyMap.size()!=0){
            log.info("获取输入批量移动或者重命名文件数量为："+keyMap.size());
            BucketManager bucketManager = createBucketManager();
            List<String> oldKeys=new ArrayList<>();
            List<String> newKeys=new ArrayList<>();
            for(Map.Entry<String,String> po:keyMap.entrySet()){
                String oldKey=po.getKey();
                if(StringUtils.isNullOrEmpty(oldKey)){
                    oldKeys.add(oldKey);
                    newKeys.add(po.getValue());
                }else {
                    log.warn("不允许输入空值键!");
                }
            }
            if(CollectionUtils.isNotEmpty(oldKeys)){
                try {
                    BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
                    for(int i=0;i<oldKeys.size();i++){
                        batchOperations.addMoveOp(bucketFrom, oldKeys.get(i), bucketTo, newKeys.get(i));
                    }
                    log.info("连接七牛云批量移动或者重命名文件开始------");
                    Response response = bucketManager.batch(batchOperations);
                    log.info("连接七牛云批量移动或者重命名文件结束......");
                    BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
                    for (int i = 0; i < oldKeys.size(); i++) {
                        BatchStatus status = batchStatusList[i];
                        String key = oldKeys.get(i);
                        if (status.code == 200) {
                            log.info("批量移动或者重命名文件成功，文件键值为："+key);
                        } else {
                            log.info("批量移动或者重命名文件失败，文件键值为："+key+";失败原因为："+status.data.error);
                        }
                    }
                } catch (QiniuException ex) {
                    outQiniuException(ex);
                }
            }
        }
    }

    /**
     * 批量复制文件
     * @param keyMap  旧键映射新键  的map
     * @param bucketFrom 原空间
     * @param bucketTo 新空间
     */
    public static void batchCopy(Map<String,String> keyMap,String bucketFrom,String bucketTo){
        if(null!=keyMap&&keyMap.size()!=0){
            try {
                log.info("获取输入批量移动或者重命名文件数量为："+keyMap.size());
                BucketManager bucketManager = createBucketManager();
                List<String> oldKeys=new ArrayList<>();
                List<String> newKeys=new ArrayList<>();
                for(Map.Entry<String,String> po:keyMap.entrySet()){
                    String oldKey=po.getKey();
                    if(StringUtils.isNullOrEmpty(oldKey)){
                        oldKeys.add(oldKey);
                        newKeys.add(po.getValue());
                    }else {
                        log.warn("不允许输入空值键!");
                    }
                }
                if(CollectionUtils.isNotEmpty(oldKeys)){
                    BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
                    for(int i=0;i<oldKeys.size();i++){
                        batchOperations.addMoveOp(bucketFrom, oldKeys.get(i), bucketTo, newKeys.get(i));
                    }
                    log.info("连接七牛云批量复制文件开始------");
                    Response response = bucketManager.batch(batchOperations);
                    log.info("连接七牛云批量复制文件结束......");
                    BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
                    for (int i = 0; i < oldKeys.size(); i++) {
                        BatchStatus status = batchStatusList[i];
                        String key = oldKeys.get(i);
                        if (status.code == 200) {
                            log.info("批量复制文件成功，文件键值为："+key);
                        } else {
                            log.info("批量复制文件失败，文件键值为："+key+";失败原因为："+status.data.error);
                        }
                    }
                }
            } catch (QiniuException ex) {
                outQiniuException(ex);
            }
        }
    }
    /**
     * 更新镜像存储空间中文件内容
     * @param key 文件键值
     */
    public static void refresh(String key){
        try {
            BucketManager bucketManager = createBucketManager();
            log.info("连接七牛云刷新文件开始，文件键值为："+key);
            bucketManager.prefetch(bucket, key);
            log.info("连接七牛云刷新文件结束，文件键值为："+key);
        } catch (QiniuException ex) {
            outQiniuException(ex);
        }
    }
}