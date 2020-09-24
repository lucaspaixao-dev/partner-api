db.createUser(
    {
        user: "partner",
        pwd: "1234",
        roles: [
            {
                role: "readWrite",
                db: "partner_service"
            }
        ]
    }
);
