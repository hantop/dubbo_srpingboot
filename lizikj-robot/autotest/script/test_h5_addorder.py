
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

pos_login_url='/miniSystem/hfive/addOrder.do'

request = {"data":
        {"shopId": 89, 
        "shopTableId":2, 
        "number":2, 
        "memo":'test order',
        "orderItems": [{'skuId':'1','nums':'2','unit':'','buyAttrIds':'','comAttrIds':''}] } 
        }

def setup():
    data.set({"shopId": 1})

def test_h5_addorder_error():
	#shopId=?&shopTableId=?&orderItems=[{"skuId":?,"nums":?,"unit":?,"buyAttrIds":?,"comAttrIds":?}]&orderId=?&number=?&memo=?
    resp = rest_api(pos_login_url, make_data(request) )
    print resp
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
