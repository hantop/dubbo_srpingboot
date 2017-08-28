
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

request_url='/miniSystem/server/checkActivate.do'

request = {"data":
        {},
        "snNum": "N9NL10093825"
        }

def test_mini_server_check():
    resp = rest_api(request_url, make_data(request) )
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
