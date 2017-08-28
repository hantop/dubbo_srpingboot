
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

request_url='/miniSystem/mini/report/saleNums.do'

request = {"data":
        {"dateType": "0"},
        "snNum": "N9NL10093825"
        }

def test_mini_report_saleNums():
    resp = rest_api(request_url, make_data(request))
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )

def test_none():
    pass

def update_type():
	data.update({"dateType": "1"})

@with_setup(setup=update_type)
def test_mini_report_saleNums_1():
    resp = rest_api(request_url, make_data(request))
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
