#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_member_recharge_url='/PCSystem/member/recharge/'

request = {}

#我的顾客-充值记录模块 功能测试
class MemberRecharge(unittest.TestCase):

    #充值记录--统计充值汇总信息
    def test_pc_simple_trans_getReportCollect(self):
        request = {}
        get_url = pc_member_recharge_url + 'getReportCollect.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
   
   #充值记录--统计充值汇总信息
    def test_pc_simple_trans_getRechargeList(self):
        request = {}
        get_url = pc_member_recharge_url + 'getRechargeList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass 
    
if __name__ == '__main__':
    unittest.main()
    