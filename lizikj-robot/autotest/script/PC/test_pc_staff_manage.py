#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_staff_manage_url='/PCSystem/staff/manage/'

request = {}

#门店管理--员工管理 功能测试
class Good(unittest.TestCase):
    
    #员工管理--获取角色列表信息
    def test_pc_staff_manage_getRoles(self):
        request = {}
        get_url = pc_staff_manage_url + 'getRoles.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass

    #员工管理--获取员工列表信息
    def test_pc_staff_manage_getList(self):
        request = {}
        get_url = pc_staff_manage_url + 'getList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #员工管理--获取员工详情信息
    def test_pc_staff_manage_getDetail(self):
        request = {"staffId":1}
        get_url = pc_staff_manage_url + 'getDetail.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    
if __name__ == '__main__':
    unittest.main()
    