#ifndef _PARSER_H
#define _PARSER_H
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <vector>
#include "operators.h"
#include "elements.h"
#include "astree.h"

namespace tina {
	class Parser {
	public:
		Astree parse(std::string lexer);
	private:
		std::vector<Element*> elements;
	};
}
#endif // !_PARSER_H

