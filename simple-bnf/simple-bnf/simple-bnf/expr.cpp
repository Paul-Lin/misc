#include "expr.h"

tina::ExprParser::ExprParser() {
	{
		tina::Precedence predence;
		predence.leftAssoc = true;
		predence.value = 1;
		map.insert(std::make_pair(std::string("<"), &predence));
	}
	{
		tina::Precedence predence;
		predence.leftAssoc = true;
		predence.value = 1;
		map.insert(std::make_pair(std::string(">"), &predence));
	}
	{
		tina::Precedence predence;
		predence.leftAssoc = true;
		predence.value = 2;
		map.insert(std::make_pair(std::string("+"), &predence));
	}
	{
		tina::Precedence predence;
		predence.leftAssoc = true;
		predence.value = 2;
		map.insert(std::make_pair(std::string("-"), &predence));
	}
	{
		tina::Precedence predence;
		predence.leftAssoc = true;
		predence.value = 3;
		map.insert(std::make_pair(std::string("*"), &predence));
	}
	{
		tina::Precedence predence;
		predence.leftAssoc = true;
		predence.value = 3;
		map.insert(std::make_pair(std::string("/"), &predence));
	}
	{
		tina::Precedence predence;
		predence.leftAssoc = false;
		predence.value = 4;
		map.insert(std::make_pair(std::string("/"), &predence));
	}
}