#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_common_url='/PCSystem/common/'

request = {}

#公共模块 功能测试
class Common(unittest.TestCase):

    #公共模块--获取单位列表
    def test_pc_common_getUnitList(self):
        request = {}
        get_url = pc_common_url + 'getUnitList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #公共模块--判断是否登录
    def test_pc_common_isLogin(self):
        request = {}
        get_url = pc_common_url + 'isLogin.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
if __name__ == '__main__':
    unittest.main()
    