#-*- coding:utf-8 -*-
ps_url = "http://192.168.12.4:8080/secondJavaDemo/TestServlet?account=guopengfei&receiver=0001"
import urllib2
import json
value ={"name":"guopengfei","age":12}
content = json.dumps(value)
req = urllib2.Request(url=ps_url,  headers = {'Content-type':'text/json'})
req.add_data(content)
rep = urllib2.urlopen(req)
print rep.read()

#https://my.oschina.net/guopengfei/blog/644445