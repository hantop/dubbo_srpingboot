#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_simple_store_url='/PCSystem/simple/store/'

request = {}

#通用模块--总店接口 功能测试
class Common(unittest.TestCase):

    #通用接口--获取门店下所有商户列表
    def test_pc_simple_trans_getShopList(self):
        request = {}
        get_url = pc_simple_store_url + 'getShopList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    
if __name__ == '__main__':
    unittest.main()
    