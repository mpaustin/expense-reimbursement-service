package com.ex.app.persistence;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection newConnection();
    void destroy();
}
