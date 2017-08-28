#!/usr/bin/env python
#coding=utf8
'''
检测版本更新的测试
'''
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

pos_update_url='/BCCSystem/app/shop/checkVersionUpdate.do'

request = {"UUID":"c032e7cb-639f-4741-8a4a-807f8f644db9",
           "platform":"1",
           "data":{"currentVersion":"10200"}
        }

def update():
    resp = rest_api(pos_update_url, make_data(request), env="http://192.168.5.175:8080/")
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass


update()