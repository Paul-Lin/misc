#ifndef _EXPR_H
#define _EXPR_H
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <map>
#include <string>

namespace tina {
	struct Precedence {
		int value;
		bool leftAssoc;
	};
	class ExprParser {
	public:
		ExprParser();

	private:
		std::map<std::string, Precedence*> map;
	};
}
#endif
