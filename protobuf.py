import requests


url = "http://localhost:9095/poc/protobuf"

# myobj = {
#     "filePath": "C:\\Users\\GOD-PC\\Documents\\WareHouse\\Personal_Projects\\POC\\ProtoBuf-POC\\output\\test",
#     "mode": "write",
#     "email": "test@gmail.com",
#     "name": "tester",
#     "number": ["1", "2", "3", "4", "5"]
# }

myobj = {
    "filePath": "C:\\Users\\GOD-PC\\Documents\\WareHouse\\Personal_Projects\\POC\\ProtoBuf-POC\\output\\test",
    "mode": "read",
    "index": 0
}

x = requests.post(url, headers={'Content-Type': 'application/json'}, json=myobj)

print(x.text)


