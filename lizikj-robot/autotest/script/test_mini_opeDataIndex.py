#LJJ write
#20170306
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

request_url='/mini/report/opeDataIndex.do'

request = {"data":
        {"dateType": 2},
        "snNum": "N9NL10093834"
        }
headers = {'content-type': 'application/json;charset=utf-8', 
           "IMEI": "N9NL10093834", 
           "flag":"3"};
    
def test_mini_opeDataIndex():
    resp = rest_api_online(request_url, make_data(request),headers)
    #resp = rest_api(request_url, make_data(request) )
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
