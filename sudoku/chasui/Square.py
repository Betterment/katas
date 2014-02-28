__author__ = 'mike'


class Cell:
    def __init__(self):
        self._value = None
        self._dimensions = list()

    @property
    def value(self):
        return self._value

    @value.setter
    def value(self, value):
        self._value = value

class Dimension:
    def __init__(self):
        self._cells =