#ifndef _CALC_1_H
#define _CALC_1_H
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
namespace tina {
	class calcu {
	public:
		void getToken();
		void match(char ch);
		void error();
		int expr();
		int term();
		int factor();
		int number();
		int digit();
		void parse();
		void command();
	private:
		static int token;
	};
}

#endif