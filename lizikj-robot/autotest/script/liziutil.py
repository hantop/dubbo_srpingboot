#!/usr/bin/env python
#coding=utf8

#!/usr/bin/env python
#coding=utf8

import sys
import traceback  

import requests
import json
import lizidata as data

def rest_api_online(api,params={},headers={},env='http://webapp.lizikj.cn/miniSystem'):
    try:
        r = requests.post(env+api, data=json.dumps(params), headers=headers ) 
        print ">>>>request headers:", r.request.headers
        print ">>>>request body:", params
        print
        print ">>>>response headers:", r.headers
        print ">>>>response body:", json.dumps(r.text.encode('utf-8'))
        return r.json()
    except Exception, e:
        traceback.print_exc() 
        
def rest_api(api, params={}, headers={}, env='http://t158.webapp.lizikj.cn', port=80):
    try:
        _headers = {'content-type': 'application/json;charset=utf-8', "IMEI": "xxxxxx", "flag":"3"};
        _headers.update(headers)
        
        r = requests.post(env+api, data=json.dumps(params), headers=_headers )

        print ">>>>request headers:", r.request.headers
        print ">>>>request body:", params
        print
        print ">>>>response headers:", r.headers
        print ">>>>response body:", json.dumps(r.text)
        return r.json()
    except Exception, e:
        traceback.print_exc() 

def make_data(req, params={}):
    rdata = req
    if not isinstance(rdata, dict): return {}

    _data = (params == None or len(params)==0 ) and data.get() or params
    for (k, v) in rdata.items():
        if isinstance(v, dict):
            rdata[k]=make_data(v)
        else:
            if _data.has_key(k): rdata[k]=_data[k]
    return rdata

if __name__ == "__main__":
    print 'util test'

    resp = rest_api('/miniSystem/security/login.do', {"shopId": 111})
    print resp
    '''
    print make_data(5)
    print make_data('a')
    print make_data(['a'])
    print make_data({"a":'1'}, {"a": "2"})
    print make_data({"a":'1', 'b':[1,2,3]}, {"a": "2", "b":[9, 8,7, 6]})
    print make_data({"a":'1', "b":3, "d":{"d1":"1", "d2":2}}, {"a": "2", "d1":"d1", "d2": 9})
    '''

