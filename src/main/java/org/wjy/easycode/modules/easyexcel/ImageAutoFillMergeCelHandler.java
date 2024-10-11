package org.wjy.easycode.modules.easyexcel;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * EasyExcel解决在合并的单元格上填充图片问题，利用poi获取合并单元格占用的行数和列数，设定填充范围
 * 
 * @author weijiayu
 * @date 2023/3/24 11:56
 */
public class ImageAutoFillMergeCelHandler implements CellWriteHandler {

    /**
     * 如果复写afterCellDispose方法，拦截的时机不同，图片会填充两次，底下会藏有一张原图
     */
    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean noImageValue = Objects.isNull(cellData) || CollectionUtils.isEmpty(cellData.getImageDataList());
        if (Objects.equals(Boolean.TRUE, isHead) || noImageValue) {
            return;
        }
        Sheet sheet = cell.getSheet();
        int mergeColNum = getMergeColNum(cell, sheet);
        int mergeRowNum = getMergeRowNum(cell, sheet);
        ImageData imageData = cellData.getImageDataList().get(0);
        imageData.setRelativeLastRowIndex(mergeRowNum - 1);
        imageData.setRelativeLastColumnIndex(mergeColNum - 1);
        CellWriteHandler.super.afterCellDataConverted(writeSheetHolder, writeTableHolder, cellData, cell, head,
            relativeRowIndex, isHead);
    }

    /**
     * 获取合并单元格占用的行数
     *
     * @param cell
     * @param sheet
     * @return int
     */
    public static int getMergeRowNum(Cell cell, Sheet sheet) {
        int mergeSize = 1;
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress cellRangeAddress : mergedRegions) {
            if (cellRangeAddress.isInRange(cell)) {
                // 获取合并的行数
                mergeSize = cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow() + 1;
                break;
            }
        }
        return mergeSize;
    }

    /**
     * 获取合并单元格占用的列数
     *
     * @param cell
     * @param sheet
     * @return int
     */
    public static int getMergeColNum(Cell cell, Sheet sheet) {
        int mergeSize = 1;
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress cellRangeAddress : mergedRegions) {
            if (cellRangeAddress.isInRange(cell)) {
                // 获取合并的列数
                mergeSize = cellRangeAddress.getLastColumn() - cellRangeAddress.getFirstColumn() + 1;
                break;
            }
        }
        return mergeSize;
    }
}
