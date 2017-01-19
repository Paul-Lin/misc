#ifndef _OPERATORS_H
#define _OPERATORS_H
#include <map>
#include <string>

namespace tina{
	struct Precedence {
		int value;
		bool leftAssoc;
	};

	class Operators {
	public:
		void add(std::string name, int prec, bool leftAssoc);

	private:
		static bool left;
		static bool right;
		static std::map<std::string, Precedence*> operators;
	};
}

void test_operators();
#endif // !_OPERATORS_H

