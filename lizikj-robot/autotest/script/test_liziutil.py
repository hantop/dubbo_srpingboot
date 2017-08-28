
from nose.tools import *
from nose.tools import with_setup

from liziutil import *
import lizidata as data

def setUp():
    data.set({'a':"A"})

def teardown():
    data.clear()


@with_setup(teardown=teardown)
def test_data():
    ok_( len(data.get() == 1)
    ok_( data.get()["a"] == "A", msg='setup data' )

def setup_a():
    data.set({'b':"B"})

@with_setup(setup_a, teardown=teardown)
def test_clear():
    ok_( len(data.get() == 1)
    ok_( data.get()["b"] == "B", msg='setup data' )

