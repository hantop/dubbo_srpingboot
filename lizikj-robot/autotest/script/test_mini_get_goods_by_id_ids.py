#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from nose.tools import with_setup

from liziutil import *

import md5

#根据商品id和规格字符串获取商品价格
pos_get_good_url='/miniSystem/market/getGoodByIdAndIds.do'

 
req = { "data": {"merchandiseId": 426, "propertyValueIds":"529" }, 'snNum':'N9NL10093825' }
def test_post_login():
    resp = rest_api(pos_get_good_url, req, headers={"flag":"3"}, env="http://t158.webapp.lizikj.cn")
    print resp['message']
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass

test_post_login()