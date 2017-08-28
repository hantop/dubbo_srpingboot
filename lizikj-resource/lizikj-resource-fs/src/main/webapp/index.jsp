<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2017/4/19
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--http://localhost:8080/download/url.do?mediaId=22--%>
<img  src="http://localhost:8080/download/url.do?mediaId=22"/>
<form method="post" action="http://upload-z2.qiniu.com"
      enctype="multipart/form-data">
    <%--<input name="key" type="hidden" value="<resource_key>">--%>
    <%--<input name="x:<custom_name>" type="hidden" value="<custom_value>">--%>
    <input name="token" type="hidden" value="8w7yON8AoCkkYgi4LeKU4wAKv73J65M9SCna2p3p:36_w6mCau8KHRjMdhLaToZmeopg=:eyJzY29wZSI6ImxpemlraiIsImRlYWRsaW5lIjoxNDkyNTg2NjkzfQ==">
    <input name="file" type="file" />
    <%--<input name="crc32" type="hidden" />
    <input name="accept" type="hidden" />--%>
    <input  type="submit" value="upload"/>
</form>
</body>
</html>
