#!/usr/bin/env python
#coding=utf8
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

request_url='/hfive/getShopConfigInfo.do'

request = {"data":{"shopId":1010193,"shopTableNo":2},"snNum": "N9NL10244356"}
headers = {'content-type': 'application/json;charset=utf-8', 
           "IMEI": "N9NL10244356", 
           "flag":"3"};
    
def test_hfive_getShopConfig():
    resp = rest_api_online(request_url, make_data(request),headers)
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
