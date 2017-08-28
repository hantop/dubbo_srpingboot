#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_sale_board_url='/PCSystem/sale/board/'

request = {}

#经营看板 功能测试
class SaleBoard(unittest.TestCase):

    #经营看板--获取营业汇总数据信息
    def test_pc_sale_board_getReportCollect(self):
        request = {}
        get_url = pc_sale_board_url + 'getReportCollect.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #经营看板-获取营业额趋势--金额统计信息
    def test_pc_sale_board_getSaleAmountReport(self):
        request = {"dateType":3}
        get_url = pc_sale_board_url + 'getSaleAmountReport.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #经营看板-获取营业额趋势--毛利润统计信息
    def test_pc_sale_board_getOrderProfitReport(self):
        request = {"dateType":2}
        get_url = pc_sale_board_url + 'getOrderProfitReport.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #经营看板--获取支付方式比例统计信息
    def test_pc_sale_board_getPayWayPercentReport(self):
        request = {"dateType":2}
        get_url = pc_sale_board_url + 'getPayWayPercentReport.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #经营看板--获取美食销售榜统计信息
    def test_pc_sale_board_getSaleGoodNumReport(self):
        request = {"dateType":3}
        get_url = pc_sale_board_url + 'getSaleGoodNumReport.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #经营看板--获取原营业额构成统计信息
    def test_pc_sale_board_getSaleComponentReport(self):
        request = {"dateType":3}
        get_url = pc_sale_board_url + 'getSaleComponentReport.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
if __name__ == '__main__':
    unittest.main()
    