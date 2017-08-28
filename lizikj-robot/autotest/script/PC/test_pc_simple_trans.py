#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_simple_trans_url='/PCSystem/simple/trans/'

request = {}

#通用模块--交易接口 功能测试
class Common(unittest.TestCase):

    #公共模块--获取单位列表
    def test_pc_simple_trans_getPayWays(self):
        request = {}
        get_url = pc_simple_trans_url + 'getPayWays.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    
if __name__ == '__main__':
    unittest.main()
    