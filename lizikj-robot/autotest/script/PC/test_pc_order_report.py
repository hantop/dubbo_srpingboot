#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_order_report_url='/PCSystem/order/report/'

request = {}

#订单管理--交易报表 功能测试
class Good(unittest.TestCase):

    #交易报表--获取交易记录信息
    def test_pc_order_report_transList(self):
        request = {}
        get_url = pc_order_report_url + 'transList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] != "9999", msg='response code:' + resp["code"] )
        pass
    
    #交易报表--获取退款记录信息
    def test_pc_order_report_refundList(self):
        request = {}
        get_url = pc_order_report_url + 'refundList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] != "9999", msg='response code:' + resp["code"] )
        pass
    
if __name__ == '__main__':
    unittest.main()
    