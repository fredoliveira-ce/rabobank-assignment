conn = new Mongo();
db = conn.getDB("robobank");

db.user.insert({ "username": "admin", "password": "pwd", "role": "ADMIN" });
db.user.insert({ "username": "user", "password": "usr", "role": "USER" });
