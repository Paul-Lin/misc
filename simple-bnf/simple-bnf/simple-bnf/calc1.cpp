#include "calc1.h"

int tina::calcu::token = 1;

void tina::calcu::getToken() {
	token = getchar();
}

void tina::calcu::error() {
	printf("parse error");
}

void tina::calcu::match(char ch) {
	if (ch == token)
		getToken();
	else
		error();
}

int tina::calcu::expr() {
	int result = term();
	while (token == '+'||token=='-') {
		if (token == '+') {
			match('+');
			result += term();
		}
		else if (token == '-') {
			match('-');
			result -= term();
		}
		
	}
	return result;
}

int tina::calcu::term() {
	int result = factor();
	while (token == '*'||token=='/') {
		if (token == '*') {
			match('*');
			result *= factor();
		}
		else if (token == '/') {
			match('/');
			result /= factor();
		}
		
	}
	return result;
}

int tina::calcu::factor() {
	int result = 0;
	if (token == '(') {
		match('(');
		result = expr();
		match(')');
	}
	else
		result=number();
	return result;
}

int tina::calcu::number() {
	int result = digit();
	while (isdigit(token))
		result = 10 * result + digit();
	return result;
}

int tina::calcu::digit() {
	int result = 0;
	if (isdigit(token)) {
		result = token - '0';
		match(token);
	}
	else
		error();
	return result;
}

void tina::calcu::parse() {
	getToken();
	command();
}


void tina::calcu::command() {
	int result = expr();
	if (token == '\n') {
		printf("the result is %d \n", result);
	}
	else
		error();
}
