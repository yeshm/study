package study.yeshm.groovy

String helloWorld() {
    def name = this.binding.getVariable("name");

    return 'Hello world. ' + name + "!"
}

