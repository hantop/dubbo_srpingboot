#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_activity_shop_url='/PCSystem/activity/shop/'

request = {}

#营销管理--单店管理 功能测试
class ActivityShop(unittest.TestCase):

    #单店管理--获取砍价活动信息
    def test_pc_activity_shop_getBargain(self):
        request = {}
        get_url = pc_activity_shop_url + 'getBargain.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #单店管理--获取套餐列表
    def test_pc_activity_shop_getPackageList(self):
        request = {}
        get_url = pc_activity_shop_url + 'getPackageList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass
    
    #单店管理--获取套餐详情
    def test_pc_activity_shop_getPackageDetail(self):
        request = {"marketDetailId":812}
        get_url = pc_activity_shop_url + 'getPackageDetail.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
        pass

    
if __name__ == '__main__':
   unittest.main()