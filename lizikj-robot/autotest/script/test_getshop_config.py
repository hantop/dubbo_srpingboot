
from nose.tools import *
from nose.tools import with_setup

from liziutil import *

pos_login_url='/miniSystem/hfive/getShopConfigInfo.do'

def test_getshop_config():
    resp = rest_api(pos_login_url, {"shopId": 89, "shopTableNo":2}, env='http://192.168.5.175:8080' )
    print resp
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
