#include "calc.h"
int tina::calc::token = 0;

void tina::calc::error(){
	printf("parse error");
}

void tina::calc::getToken() {
	tina::calc::token = getchar();
}

void tina::calc::match(char ch) {
	if (token == ch)
		getToken();
	else
		error();
}
int tina::calc::expr() {
	int result = term();
	while (token == '+') {
		match('+');
		result += term();
	}
	return result;
}

int tina::calc::term() {
	int result = factor();
	while (token == '*') {
		match('*');
		result *= factor();
	}
	return result;
}

int tina::calc::factor() {
	int result = 0;
	if (token == '(') {
		match('(');
		result = expr();
		match(')');
	}
	else
		result = number();
	return result;
}

int tina::calc::number() {
	int result = digit();
	while (isdigit(token)) {
		result = 10 * result + digit();
	}
	return result;
}

int tina::calc::digit() {
	int result=0;
	if (isdigit(token)) {
		result = token - '0';
		match(token);
	}
	else
		error();
	return result;

}

void tina::calc::parse() {
	getToken();
	command();
}

void tina::calc::command() {
	int result = expr();
	if (token == '\n')
		printf("the result is: %d\n", result);
	else
		error();
}




