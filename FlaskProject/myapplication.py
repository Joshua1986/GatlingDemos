# /usr/bin/python
# -*- coding: utf-8 -*-
__author__ = 'dongmu'


from flask import Flask, render_template, request, jsonify
app = Flask(__name__)
num = 1


@app.route('/getHtml', methods=['GET', 'POST'])
def my_html():
    global num
    name = request.args["name"] if "name" in request.args else "World"
    mynum = num
    num += 1
    return render_template('my_html.html', name=name, number=mynum)


@app.route('/getJson', methods=['GET', 'POST'])
def my_json():
    global num
    name = request.args["name"] if "name" in request.args else "default"
    jsonStr = {"name": name, "number": num}
    num += 1
    return jsonify(jsonStr)


@app.route('/getFormJson', methods=['POST'])
def my_form_json():
    global num
    name = request.form["name"] if "name" in request.form else "default"
    print request.form["name"]
    jsonStr = {"name": name, "number": num}
    num += 1
    return jsonify(jsonStr)

if __name__ == '__main__':
    app.run(host="127.0.0.1", port=8888, debug=False)

