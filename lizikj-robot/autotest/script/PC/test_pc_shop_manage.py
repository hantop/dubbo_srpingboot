#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_shop_manage_url='/PCSystem/shop/manage/'

request = {}

#门店管理--单店管理 功能测试
class ShopManage(unittest.TestCase):

    #单店管理--获取营业设置
    def test_pc_shop_manage_getConfig(self):
        request = {}
        get_url = pc_shop_manage_url + 'getConfig.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #单店管理--获取高级功能信息
    def test_pc_shop_manage_getHighFun(self):
        request = {}
        get_url = pc_shop_manage_url + 'getHighFun.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
        
    
if __name__ == '__main__':
    unittest.main()
    