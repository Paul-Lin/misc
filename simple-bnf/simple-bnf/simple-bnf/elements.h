#ifndef _ELEMENTS_H
#define _ELEMENTS_H
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string>
#include <vector>
#include "astree.h"

namespace tina{
	class Element {
	public:
		virtual void parse(std::string lexer,std::vector<Astree> res)=0;
		virtual bool match(std::string lexer)=0;
	};
}
#endif // !_ELEMENTS_H

