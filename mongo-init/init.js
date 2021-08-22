conn = new Mongo();
db = conn.getDB("robobank");

db.user.insert({ "username": "admin", "password": "pwd", "document": "00000000000", "role": "ADMIN" });
db.user.insert({ "username": "peggy.burns", "password": "usr", "document": "109327486", "role": "USER" });
db.user.insert({ "username": "randall.gordon", "password": "usr", "document": "50293847502", "role": "USER" });
db.user.insert({ "username": "douglas.sanchez", "password": "usr", "document": "7509823475", "role": "USER" });
db.user.insert({ "username": "michele.clark", "password": "usr", "document": "652794353", "role": "USER" });
