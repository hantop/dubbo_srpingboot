#!/usr/bin/env python
#coding=utf8

import sys
import urllib, urllib2
import httplib
import chardet
import traceback  

import json

from nose.plugins.attrib import attr

class DataVar:
    data = {}

@attr(env='dev')
def get():
    return DataVar.data

def set(_data):
    DataVar.data = _data
    return get()

def update(data):
    DataVar.data.update(data)
    return DataVar.data

def clear():
    DataVar.data.clear()
    return get()

if __name__ == "__main__":
    print 'data test'
    print get()
    print set({'a': 'A'})
    print update({'b': 'B'})
    print clear()

