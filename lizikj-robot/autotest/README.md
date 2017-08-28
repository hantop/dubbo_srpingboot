接口自动化测试脚本说明
=
1) 安装python2.7，下载地址 https://www.python.org/downloads/<br />
　　安装完成后，设置环境变量加入path<br />
<br />
2) 安装工具 easy_install<br />
   下载文件 http://pypi.python.org/pypi/setuptools<br />
   安装命令 python setup.py install<br />
   安装完成后，就可以用easy_install安装第三方包了<br />
<br />
3) 安装requests包<br />
   easy_install requests
<br />
4) 安装nosetests<br />
   easy_install nose<br />
<br />
   nosetests常用命令<br />
   nosetests -h<br />
   nosetests --nocapture<br />
   nosetests test_pos_login.py<br />
<br />
5) 测试工具包<br />
   lizidata.py  # 数据管理，为测试用例提供数据<br />
   liziutil.py  # 这里面有两个方法rest_api (http接口访问)和make_data (生成测试用例)<br />

<br />
6) 测试文件以test_开头，后接请求名<br />
   在测试文件里，提供两个变量，pos_login_url和request_json<br />
   多个测试用例<br />
   对不同的测试用例可指定不同的setup，参见nosetests的@with_setup<br />
<br />
7) 断言assert<br />
   见包nose.tools<br />
   help(nose.tools)<br />
