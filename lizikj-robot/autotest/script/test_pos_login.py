#!/usr/bin/env python
#coding=utf8

from nose.tools import *
from nose.tools import with_setup

from liziutil import *

import md5

pos_login_url='/miniSystem/security/login.do'

#-----BEGIN PUBLIC KEY-----
# -----END PUBLIC KEY-----
pub_key='MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK68zct9KmQtGltmq+OyDjY5oxH8LerUYOKgGWqXgImJppUWwqd6AlI/vCNJE6Xt9pcQeK+eGlBbaWx4CqlkN4co1DafCzYdNszpduspNqUhHhtSsQ2UyH7L3byvUiPr1r3Am4zPVET1mGnVio+RPg6QAM1qx0IOLcMA4EaBx63QIDAQAB'

#-----BEGIN PRIVATE KEY-----
#-----END PRIVATE KEY-----
priv_key='MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMrrzNy30qZC0aW2ar47IONjmjEfwt6tRg4qAZapeAiYmmlRbCp3oCUj+8I0kTpe32lxB4r54aUFtpbHgKqWQ3hyjUNp8LNh02zOl26yk2pSEeG1KxDZTIfsvdvK9SI+vWvcCbjM9URPWYadWKj5E+DpAAzWrHQg4twwDgRoHHrdAgMBAAECgYB24xJPx5xSOoAofaaKH27DH1Cvr1EEK/rZTNnBc2i1GsmZIA+vS6fy71MKNY3aUrkcTTn8YtC4/oOWqEz1ZObPimnQ1RAVIy1LIQLrWnsnjt8MnMn+z/gJnLdLnpMgZ5aBTbRp7yKVntFeXyXj1M7ZgAS6u0+gcNJ9CcYG433gQJBAP1ktAw1rkvFzkojCoqhZ5y3FET9JyWLvqyNmuvvJYMrhNff5hBRWV1dAwWkngRMoI0+X5blOIitXf94DIQqaECQQDNAi5eZnRpzSI7pKtzxF2ApKNi/c7lhOyigAEs4joIlwCiiDS6QIbEXn7tk+sQ7JJLrRlOX42d+mmYxxnqjN+9AkEA7bUS0oFpSNv/cbz9np9bu8Bj23RAzRATh3wv4TUvU6X3sSvxf01RMCyZMSoMoIZBg7R+ePMg8CLvaelX8J+BAQJARpLmpYa9sqJ0k3W3tC24Ro3m1AspmShFbvdK7dhZQ2eoVUkWa3W9wjUxzKxrGzUXArl+E36sZS//2EjMdR3PQJAVnv6MGA6BN6LOm/7R/bjAE7Wksw7nKJv5B0aGx2gsxIvLfmToyWd0mP4now8Gnkv4C3O1TS1/VjsCzpd3FTlFg=='

'''
参数名    必选    类型    说明
publicKey    是    String    公钥
account    是    string    账号
password    是    string    密码 = MD5（密码+sn）
'''
def test_post_login():
    m1 = md5.new()
    m1.update('123456' + 'agfdfsRR$%%FGS#T^REWTBFGFDD()%$RF<>JGHNVGDER^%$$TYHF#KFGHKFXWU_)(*&^%QADFGHKHYTFVM,uGFFytr')
    m5 = m1.hexdigest()
    print m5

    req = { "data": {"publicKey": pub_key, "account":"13829797004", "password": m5 }, 'snNum':'N9NL10093825' }

    resp = rest_api(pos_login_url, req, headers={"flag":"1"} )
    print resp['message']
    ok_( resp["code"] == "0000", msg='response code:' + resp["code"] )
    pass
