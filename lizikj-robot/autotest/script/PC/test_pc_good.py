#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from pc_liziutil import *
import unittest

pc_good_url='/PCSystem/good/'

request = {}

#美食管理--门店美食 功能测试
class Good(unittest.TestCase):

    #门店美食--美食清单
    def test_pc_good_getList(self):
        request = {}
        get_url = pc_good_url + 'getList.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] != "9999", msg='response code:' + resp["code"] )
        pass
    
    #门店美食--更新美食信息
    '''
    def test_pc_good_addGood(self):
        request = {
                   "merchandiseName":"快快快",
                   "merchandiseCode":"56789",
                   "imageUrl":"http://192.168.5.175:8088/static/image/shop/o/138287665635984034879.jpg",
                   "imageThumbnails":"",
                   "categoryIds":"1338,1340,",
                   "unitId":"5",
                   "unitName":"瓶",
                   "propertyNames":"[]",
                   "salePrice":"12",
                   "costPrice":"1",
                   "stockNum":"5",
                   "describe":"这是个意外",
                   "isWeight":"2"
                }
        get_url = pc_good_url + 'updateGood.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] != "9999", msg='response code:' + resp["code"] )
        pass
    '''
    #门店美食--美食清单
    def test_pc_good_getDetail(self):
        request = {"merchandiseId":702}
        get_url = pc_good_url + 'getDetail.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] != "9999", msg='response code:' + resp["code"] )
        pass
    
    #门店美食--美食清单
    def test_pc_good_getData(self):
        request = {"merchandiseId":702}
        get_url = pc_good_url + 'getData.do'
        resp = rest_api(get_url, make_data(request) )
        ok_( resp["code"] != "9999", msg='response code:' + resp["code"] )
        pass
        
    
if __name__ == '__main__':
    unittest.main()
    