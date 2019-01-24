//序列化form参数为json对象 用法var data = $('#formId').serializeJSON()
(function ($) {
    $.fn.serializeJSON = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                if (!ExcelHelper.utils.isEmpty(this.value)) {
                    o[this.name].push(this.value);
                }
            } else {
                if (!ExcelHelper.utils.isEmpty(this.value)) {
                    o[this.name] = this.value;
                }
            }
        });
        return o;
    };
})(jQuery);

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }

    return fmt;
};

var ExcelHelper = function (options) {
    this.init(options);
};

ExcelHelper.prototype.options = {
    fileName: null,//导出的文件名称 不带后缀，会自动添加时间戳和后缀
    header: [],//excel表头配置 支持用rowspan、colspan控制表头合并 必填
    defaultWidth: 10,//默认列宽
    autoWidth: true,//自动计算列宽
    columns: [],//数据列配置 必填
    data: null,//静态数据，通过其他方式准备好数据供导出的场景适用 data、ajax必填一
    maxRow: 1000000,//导出最大数据量，分页获取数据时，达到这个数就不再获取
    ajax: {//动态获取数据配置 data、ajax必填一
        url: '',//获取数据url 必填
        method: 'GET',
        data: {},//查询参数
        pageSize: 10000,//每页大小 默认10000
        initialPageNo: 0,//初始分页索引 默认0
        pageSizeKey: "pageSize",//pageSize的key，以支持后端不同的命名方式，如"size"
        pageNoKey: "pageNo",//pageNo的key，以支持后端不同的命名方式，如"page"
        success: null,//成功回调函数，可以做数据处理工作。如有设置，请必须返回处理后的值
        error: null//失败回调函数，可以做失败提示
    },
    xlsxOptions: {
        bookType: 'xlsx',
        bookSST: false,
        type: 'binary',
        compression: true
    },
    tableStyle: null,//表格样式 如 "table,th,td{text-align: right}"
    tablePreviewStyle: "table {border-collapse: collapse;word-break: keep-all;}table, td, th {border: 1px solid black;}",//预览表格样式
    previewEl: null,//调试的时候在页面预览表格的元素ID
    doExport: true,//是否执行导出，默认"是"，以方便调试时，只预览，暂不导出
    mask: null,//function 可在导出开始时打开遮罩层
    unmask: null,//function 可在导出结束后关闭遮罩层
    testRepeat: 0,//测试用数据重复次数
};
ExcelHelper.prototype.data = null;
ExcelHelper.prototype.htmlContent = null;
ExcelHelper.prototype.errorFlag = false;
ExcelHelper.prototype.workbook = null;
ExcelHelper.prototype.sheet = null;
ExcelHelper.prototype.row = null;
ExcelHelper.prototype.range = null;//显示的单元格数据
ExcelHelper.prototype.mergeData = null;//单元格合并数据
ExcelHelper.prototype.colWidth = null;//列宽数据

ExcelHelper.utils = {
    REGX_HTML_ENCODE: /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g,
    alert: function (msg) {
        alert(msg);
    },
    isEmpty: function (obj) {
        if (typeof obj === "undefined" || obj === null || obj === "") {
            return true;
        } else {
            return false;
        }
    },
    isInteger: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }
        return /^[1-9]\d*$/.test(value.toString());
    },
    //获得字符串实际长度，中文2，英文1
    stringLength: function (str) {
        if (this.isEmpty(str)) {
            return 0;
        }

        var realLength = 0, len = str.length, charCode = -1;
        for (var i = 0; i < len; i++) {
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) realLength += 1;
            else realLength += 2;

            //英文大写再加一
            if (charCode >= 65 && charCode <= 90) {
                realLength += 1;
            }
        }
        return realLength;
    },
    log: function () {
        var log = console.log;
        var msg = new Date().format("yyyy-MM-dd hh:mm:ss.S") + " # " + arguments[0];

        arguments[0] = msg;

        log.apply(console, arguments);
    },
    indexToLetter: function (index) {
        if (!this.isInteger(index) && index !== 0) {
            this.alert("待转换成字母的索引值不是数字");
        }

        var s = "";

        var m = parseInt(index / 26);

        if (m > 0) {
            s += this.indexToLetter(m - 1);
            index -= 26;
        }

        var charCode = index + 65;
        s += String.fromCharCode(charCode);

        return s;
    },
    getTimeStr: function () {
        var now = new Date();

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();          //秒

        var str = '' + year;

        str = this.appendTimeItem(str, month);
        str = this.appendTimeItem(str, day);
        str = this.appendTimeItem(str, hh);
        str = this.appendTimeItem(str, mm);
        str = this.appendTimeItem(str, ss);

        return str;
    },
    appendTimeItem: function (str, timeItem) {
        if (timeItem < 10) {
            str += "0";
        }
        str += timeItem;

        return str;
    },
    saveAs: function (obj, fileName) {
        var a = document.createElement('a');
        a.download = fileName || '下载';
        a.href = URL.createObjectURL(obj);
        a.click();
        setTimeout(function () {
            URL.revokeObjectURL(obj);
        }, 100);
    },
    s2ab: function (s) {
        var buf = new ArrayBuffer(s.length);
        var view = new Uint8Array(buf);
        for (var i = 0; i !== s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
        return buf;
    }
};

ExcelHelper.prototype.init = function (options) {
    ExcelHelper.utils.log("input options:%o", options);
    $.extend(true, this.options, options);
    ExcelHelper.utils.log("extended options:%o", this.options)
};

ExcelHelper.prototype.initStatus = function () {
    this.data = [];
    this.htmlContent = '';
    this.errorFlag = false;
    this.sheet = {};
    this.workbook = {
        "SheetNames": [
            "Main"
        ],
        "Sheets": {
            "Main": this.sheet
        }
    };
    this.row = 0;
    this.range = {s: {c: 10000000, r: 10000000}, e: {c: 0, r: 0}};
    this.mergeData = [];
    this.colWidth = {};
};

ExcelHelper.prototype.currentSheet = function () {
    return this.sheet;
};

ExcelHelper.prototype.prepareData = function () {
    ExcelHelper.utils.log("prepareData...");
    if (this.options.data) {
        ExcelHelper.utils.log("use local data");
        this.data = this.options.data;

        this.processData();
        return;
    }

    if (this.options.ajax.url) {
        var params = this.options.ajax.data;
        params[this.options.ajax.pageNoKey] = this.options.ajax.initialPageNo;
        params[this.options.ajax.pageSizeKey] = this.options.ajax.pageSize;

        this.loadData(params);
        return;
    }

    ExcelHelper.utils.alert("没有配置data，也没有配置ajx，将不导出任何数据。");
};

ExcelHelper.prototype.loadData = function (params) {
    ExcelHelper.utils.log("loadData... ajax url:%s params:%o", this.options.ajax.url, params);

    var _this = this;

    $.ajax({
        type: this.options.ajax.method,
        url: this.options.ajax.url,
        dataType: "json",
        data: params,
        async: true,
        success: function (result) {
            var data = [];
            if (result.status == 0) {
                if (_this.options.ajax.success) {
                    data = _this.options.ajax.success(result);
                } else {
                    data = result.data;
                }
                if (!ExcelHelper.utils.isEmpty(data)) {
                    _this.loadDataToExcel(data);
                    _this.loadTestData(data);
                }
            } else {
                ExcelHelper.utils.alert(result.statusInfo);
                _this.errorFlag = true;
            }

            if (this.errorFlag
                || ExcelHelper.utils.isEmpty(data)
                || data.length < params[_this.options.ajax.pageSizeKey]
                || (_this.row + 1) >= _this.options.maxRow) {

                ExcelHelper.utils.log("ajax end, data length:%d", _this.row + 1);
                _this.updateSheetProperties();
            } else {
                params[_this.options.ajax.pageNoKey]++;
                setTimeout(function () {
                    _this.loadData(params);
                }, 100);
            }
        },
        error: function (xhr, status, err) {
            if (_this.options.ajax.error) {
                _this.options.ajax.error(result);
            } else {
                ExcelHelper.utils.alert('系统异常');
                _this.unmask();
            }
        }
    });
};

ExcelHelper.prototype.updateRange = function (row, col) {
    if (this.range.s.r > row) {
        this.range.s.r = row;
    }
    if (this.range.s.c > col) {
        this.range.s.c = col;
    }
    if (this.range.e.r < row) {
        this.range.e.r = row;
    }
    if (this.range.e.c < col) {
        this.range.e.c = col;
    }
};

ExcelHelper.prototype.calcRef = function () {
    ExcelHelper.utils.log("calcRef...%o", this.range);

    var ref = XLSX.utils.encode_range(this.range);

    return ref;
};

ExcelHelper.prototype.collectColWidth = function (i, j, value) {
    var width;

    if (ExcelHelper.utils.isEmpty(value)) {
        width = this.options.defaultWidth;
    } else {
        width = ExcelHelper.utils.stringLength(value);
        // width += 2;
    }

    if (ExcelHelper.utils.isEmpty(this.colWidth[i])) {
        this.colWidth[i] = {};
    }

    this.colWidth[i][j] = width;
};

ExcelHelper.prototype.calcWidth = function () {
    ExcelHelper.utils.log("calcWidth...");

    var cols = [];
    var headerFirstRow = null;

    if (!ExcelHelper.utils.isEmpty(this.options.header)) {
        headerFirstRow = this.options.header[0];
    }

    if (this.options.autoWidth === true) {
        var colCount = this.options.header[0].length;

        for (var j = 0; j < colCount; j++) {
            cols.push({
                wch: this.options.defaultWidth
            });
        }

        for (var i = 0; i < this.row; i++) {
            for (var j = 0; j < colCount; j++) {
                var headerItem = headerFirstRow[j];
                var width = headerItem.width;

                if (!ExcelHelper.utils.isEmpty(width)) {
                    cols[j]['wch'] = width;
                } else if (cols[j]['wch'] < this.colWidth[i][j]) {
                    cols[j]['wch'] = this.colWidth[i][j];
                }
            }
        }
    } else {
        if (headerFirstRow) {
            for (var j = 0; j < headerFirstRow.length; j++) {
                var headerItem = headerFirstRow[j];
                var width = headerItem.width;
                var obj = {wch: width || this.options.defaultWidth};

                cols.push(obj);
            }
        }
    }

    return cols;
};

ExcelHelper.prototype.updateSheetProperties = function () {
    ExcelHelper.utils.log("updateSheetProperties...");

    var sheet = this.currentSheet();
    var cols = this.calcWidth();
    var ref = this.calcRef();

    // this.renderPreview(_this.htmlContent);

    sheet["!cols"] = cols;
    sheet['!ref'] = ref;

    sheet['!merges'] = this.mergeData;

    if (!this.options.doExport) {

        return;
    }

    this.writeResponse();
    this.unmask();
    this.initStatus();
};

ExcelHelper.prototype.renderTHead = function () {
    ExcelHelper.utils.log("renderTHead...");
    var sheet = this.currentSheet();

    if (!ExcelHelper.utils.isEmpty(this.options.header)) {
        for (var i = 0; i < this.options.header.length; i++) {
            var headerRow = this.options.header[i];

            for (var j = 0; j < headerRow.length; j++) {
                var headerItem = headerRow[j];
                var indexLetter = ExcelHelper.utils.indexToLetter(j);
                var obj = {
                    "v": headerItem.title
                };

                if (headerItem.style) {
                    obj.s = headerItem.style;
                }

                sheet[indexLetter + (i + 1)] = obj;

                this.updateRange(i, j);
                this.collectColWidth(i, j, headerItem.title);

                if (headerItem.rowspan || headerItem.colspan) {
                    this.mergeData.push({
                        "s": {
                            "c": j,
                            "r": i
                        },
                        "e": {
                            "c": j + (headerItem.colspan || 1) - 1,
                            "r": i + (headerItem.rowspan || 1) - 1
                        }
                    });
                }
            }
            this.row += 1;
        }
    }
};

ExcelHelper.prototype.loadDataToExcel = function (data) {
    ExcelHelper.utils.log("loadDataToExcel...data length:%d current row:%d", data.length, this.row);

    var dataLength = data.length;

    if (dataLength > 0) {
        if (this.options.testRepeat > 0) {
            var firstObj = data[0];

            Object.keys(firstObj).forEach(function (key) {
                if (typeof firstObj[key] == "string") {
                    firstObj[key] = "===测试文件===";
                } else if (typeof firstObj[key] == "number") {
                    firstObj[key] = 0;
                }
            });
        }

        for (var i = 0; i < dataLength; i++) {
            var dataItem = data[i];

            for (var j = 0; j < this.options.columns.length; j++) {
                var column = this.options.columns[j];
                var dataIndex = column.dataIndex;

                var value = dataItem[dataIndex];

                this.addCol(j, value, dataItem);
            }

            this.row += 1;
        }
    }
};

ExcelHelper.prototype.loadTestData = function (data) {
    ExcelHelper.utils.log("loadTestData... data length:%d testRepeat:%d", data.length, this.options.testRepeat);

    if (this.options.testRepeat <= 0 || data.length <= 0) {
        return;
    }

    var originDataLength = data.length;
    var copyArray = [];

    for (var r = 0; r < this.options.testRepeat; r++) {
        var firstObj = $.extend({}, data[0]);

        Object.keys(firstObj).forEach(function (key) {
            if (typeof firstObj[key] == "string") {
                firstObj[key] = "===测试 " + (r + 1) + " ===";
            } else if (typeof firstObj[key] == "number") {
                firstObj[key] = 0;
            }
        });

        for (var i = 0; i < originDataLength; i++) {
            if (i == 0) {
                copyArray.push(firstObj);
            } else {
                copyArray.push(data[i]);
            }
        }
    }

    ExcelHelper.utils.log("test data length:%d", data.length);
    this.loadDataToExcel(copyArray);
};

/**
 * 新增一列
 * @param row
 * @param col
 * @param value
 * @param object
 */
ExcelHelper.prototype.addCol = function (col, value, object) {
    // ExcelHelper.utils.log("addCol... col:%d value:%s object:%o", col, value, object);
    var sheet = this.currentSheet();
    var column = this.options.columns[col];

    if (column.renderer) {
        value = column.renderer(value, object);
    }

    if (typeof value == 'string') {
        value = value.replace(/\\/g, '\\\\');
    }


    var indexLetter = ExcelHelper.utils.indexToLetter(col);

    sheet[indexLetter + (this.row + 1)] = {
        "v": value,
        "t": column.type ? column.type : "s",
        "s": column.style ? column.style : {}
    };

    this.updateRange(this.row, col);
    this.collectColWidth(this.row, col, value);
};

ExcelHelper.prototype.renderPreview = function (strTemp) {
    ExcelHelper.utils.log("renderPreview...");

    if (this.options.previewEl) {
        if (this.options.tablePreviewStyle) {
            var styleStr = '<style type="text/css">' + this.options.tablePreviewStyle + '</style>';
        }

        $("#" + this.options.previewEl).html(styleStr);
        $("#" + this.options.previewEl).append(strTemp);
    }
};

ExcelHelper.prototype.writeResponse = function () {
    ExcelHelper.utils.log('write workbookOut');
    var workbookOut = XLSX.write(this.workbook, this.options.xlsxOptions);
    var fileNmae = this.options.fileName + ExcelHelper.utils.getTimeStr() + ".xlsx";

    ExcelHelper.utils.log('saveAs %s', fileNmae);
    ExcelHelper.utils.saveAs(new Blob([ExcelHelper.utils.s2ab(workbookOut)], {
        type: 'application/octet-stream'
    }), fileNmae)
};

ExcelHelper.prototype.mask = function () {
    ExcelHelper.utils.log("mask...");

    if (this.options.mask) {
        this.options.mask();
        return;
    }

    if (typeof lv !== "undefined") {
        lv.loading(
            {
                show: true,
                text: '导出中···'
            }
        );
    }
};

ExcelHelper.prototype.unmask = function () {
    ExcelHelper.utils.log("unmask...");

    if (this.options.unmask) {
        this.options.unmask();
        return;
    }

    if (typeof lv !== "undefined") {
        lv.loading(
            {
                show: false
            }
        );
    }
};

ExcelHelper.prototype.processData = function () {
    ExcelHelper.utils.log("processData...");

    if (this.errorFlag) {
        this.unmask();
        return;
    }

    this.loadDataToExcel(this.data);
    this.loadTestData(this.data);
    this.updateSheetProperties();
};

ExcelHelper.prototype.export = function () {
    this.mask();
    this.initStatus();
    this.renderTHead();
    this.prepareData();
};
