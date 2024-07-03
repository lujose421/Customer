package com.nttdata.customer.exception;

import java.util.Date;

public record ErrorMessage(int statusCode, Date timestamp, String message) {
}
