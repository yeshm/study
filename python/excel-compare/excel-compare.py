# -*- coding: utf-8 -*-
import os
import time

import xlrd

base_location = "/Users/yeshm/work/luckin/配货单核对/"
today = time.strftime('%Y%m%d', time.localtime(time.time()))


def get_business_code_list(day, dir_name):
    full_location = base_location + day + os.sep + dir_name
    files = os.listdir(full_location)

    print("files under %s: %s" % (dir_name, files))
    business_code_list = []

    for file in files:
        print("process file: %s" % file)

        if file.startswith("~") or (not file.endswith(".xlsx") and not file.endswith(".xls")):
            continue

        print("read file: %s" % file)

        excel_file = xlrd.open_workbook(full_location + "/" + file)
        # 取第一个sheet
        sheet = excel_file.sheet_by_index(0)
        # 第一列
        cols = sheet.col_values(0)
        print("read %d cols" % len(cols))
        print("cols content: %s" % cols)
        business_code_list += cols[1:len(cols)]
        print("business_code_list size:%d" % len(business_code_list))

    return business_code_list


def compare(day=None):
    if day is None:
        day = today

    lucky_business_code_list = get_business_code_list(day, 'lucky')
    wms_business_code_list = get_business_code_list(day, 'wms')

    # 计算lucky多出的数据
    lucky_business_code_extra = []
    for item in lucky_business_code_list:
        if item not in wms_business_code_list:
            lucky_business_code_extra.append(item)

    print("lucky_business_code_extra size: %d" % len(lucky_business_code_extra))
    print("lucky_business_code_extra: %s" % lucky_business_code_extra)

    # 计算wms多出的数据
    wms_business_code_extra = []
    for item in wms_business_code_list:
        if item not in lucky_business_code_list:
            wms_business_code_extra.append(item)

    print("wms_business_code_extra size: %d" % len(wms_business_code_extra))
    print("wms_business_code_extra: %s" % wms_business_code_extra)


if __name__ == '__main__':
    # compare("20181012")
    compare()
