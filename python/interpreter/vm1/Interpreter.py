# coding:utf-8
class Interpreter:
    def __init__(self):
        self.stack = []

    def LOAD_VALUE(self, number):
        self.stack.append(number)

    def ADD_TWO_VALUES(self):
        num1 = self.stack.pop()
        num2 = self.stack.pop()
        self.stack.append(num1 + num2)

    def PRINT_ANSWER(self):
        print(self.stack.pop())

    def run_code(self, what_to_execute):
        instructions = what_to_execute["instructions"]
        numbers = what_to_execute["numbers"]
        for each_step in instructions:
            instruction, arguments = each_step
            if instruction == "LOAD_VALUE":
                number = numbers[arguments]
                self.LOAD_VALUE(number)
            elif instruction == "ADD_TWO_VALUES":
                self.ADD_TWO_VALUES()
            elif instruction == "PRINT_ANSWER":
                self.PRINT_ANSWER()


what_to_execute = {
    "instructions": [("LOAD_VALUE", 0),
                     ("LOAD_VALUE", 1),
                     ("ADD_TWO_VALUES", None),
                     ("LOAD_VALUE", 2),
                     ("ADD_TWO_VALUES", None),
                     ("PRINT_ANSWER", None)],
    "numbers": [7, 5,54]
}

what_to_execute={
    "instructions":[("LOAD_VALUE",0),
                    ("STORE_NAME",0),
                    ("LOAD_VALUE",1),
                    ("STORE_NAME",1),
                    ("LOAD_NAME",0),
                    ("LOAD_NAME",1),
                    ("ADD_TWO_VALUES",None),
                    ("PRINT_ANSWER",None)],
    "numbers":[1,2],
    "names":["a","b"]
}
interpreter = Interpreter()
interpreter.run_code(what_to_execute)
