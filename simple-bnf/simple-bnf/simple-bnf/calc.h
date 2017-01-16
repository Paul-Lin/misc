#ifndef _CALC_H
#define _CALC_H

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

namespace tina {

	class calc {
	public:
		void error();

		int expr();

		int term();

		int factor();

		int number();

		int digit();

		void getToken();

		void match(char c);

		void parse();
		
		void command();
	private:
		static int token;


	};
}



#endif