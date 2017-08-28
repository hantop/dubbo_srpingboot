#!/usr/bin/env python
#coding=utf8

import json
import pc_lizidata as data
import requests
import sys
import traceback


def rest_api(api, params={}, headers={}, env='http://192.168.2.48', port=8888):
    try:
        _params = {'USER_SESSION_ID':'5fc385a7-4318-4318-a9fd-6bcba0c6b5e71703231654008'}
        _headers = {'content-type': 'application/json;charset=utf-8'};
        _headers.update(headers)
        r = requests.post(env+":"+str(port)+api, data=json.dumps(params), params=_params, headers=_headers )
        print ">>>>request url:", env+":"+str(port)+api
        print ">>>>request body:", params
        print ">>>>response headers:", r.headers
        print ">>>>response body:", r.text.decode()
        print
        return json.loads(r.text.decode())
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

