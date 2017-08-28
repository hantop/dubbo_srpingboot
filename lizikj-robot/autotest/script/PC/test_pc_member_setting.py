#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_get_member_setting_url='/PCSystem/member/setting/'

request = {}

#我的顾客 会员设置 功能测试
class memberSetting(unittest.TestCase):

    #会员设置-获取开关设置
    def test_pc_member_setting_getOnOff(self):
        request = {}
        get_url = pc_get_member_setting_url + 'getOnOff.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #会员设置-获取会员等级信息
    def test_pc_member_setting_getLevelInfo(self):
        request = {"vip":3}
        get_url = pc_get_member_setting_url + 'getLevelInfo.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass

if __name__ == '__main__':
   unittest.main()