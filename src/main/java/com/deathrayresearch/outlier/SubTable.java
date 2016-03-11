package com.deathrayresearch.outlier;

import com.deathrayresearch.outlier.columns.BooleanColumn;
import com.deathrayresearch.outlier.columns.CategoryColumn;
import com.deathrayresearch.outlier.columns.Column;
import com.deathrayresearch.outlier.columns.ColumnType;
import com.deathrayresearch.outlier.columns.FloatColumn;
import com.deathrayresearch.outlier.columns.IntColumn;
import com.deathrayresearch.outlier.columns.LocalDateColumn;
import com.deathrayresearch.outlier.columns.LocalDateTimeColumn;
import com.deathrayresearch.outlier.columns.LocalTimeColumn;
import com.deathrayresearch.outlier.columns.PeriodColumn;
import com.deathrayresearch.outlier.columns.TextColumn;

import java.util.List;

/**
 * A specialization of the standard Table used for tables formed by grouping operations on a
 * Table
 */
class SubTable extends Table {

  /** The values that will be summarized on */
  private List<String> values;

  /**
   * Returns a new SubTable from the given table that will include summaries for the given values
   *
   * @param original  The table from which this one was derived
   */
  public SubTable(Table original) {
    super(original.name(),
        original.emptyCopy().columns().toArray(new Column[original.columnCount()]));
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

  public void addRow(int rowIndex, Table sourceTable) {
    for (int i = 0; i < columnCount(); i++) {
      Column column = column(i);
      ColumnType type = column.type();
      switch (type) {
        case FLOAT :
          FloatColumn floatColumn = (FloatColumn) column;
          floatColumn.add(sourceTable.floatColumn(i).get(rowIndex));
          break;
        case INTEGER:
          IntColumn intColumn = (IntColumn) column;
          intColumn.add(sourceTable.intColumn(i).get(rowIndex));
          break;
        case BOOLEAN:
          BooleanColumn booleanColumn = (BooleanColumn) column;
          booleanColumn.add(sourceTable.booleanColumn(i).get(rowIndex));
          break;
        case LOCAL_DATE:
          LocalDateColumn localDateColumn = (LocalDateColumn) column;
          localDateColumn.add(sourceTable.localDateColumn(i).getInt(rowIndex));
          break;
        case LOCAL_TIME:
          LocalTimeColumn localTimeColumn = (LocalTimeColumn) column;
          localTimeColumn.add(sourceTable.localTimeColumn(i).getInt(rowIndex));
          break;
        case LOCAL_DATE_TIME:
          LocalDateTimeColumn localDateTimeColumn = (LocalDateTimeColumn) column;
          localDateTimeColumn.add(sourceTable.localDateTimeColumn(i).getLong(rowIndex));
          break;
        case PERIOD:
          PeriodColumn periodColumn = (PeriodColumn) column;
          periodColumn.add(sourceTable.periodColumn(i).getInt(rowIndex));
          break;
        case TEXT:
          TextColumn textColumn = (TextColumn) column;
          textColumn.add(sourceTable.textColumn(i).get(rowIndex));
          break;
        case CAT:
          CategoryColumn categoryColumn = (CategoryColumn) column;
          categoryColumn.add(sourceTable.categoryColumn(i).get(rowIndex));
          break;
        default:
          throw new RuntimeException("Unhandled column type updating columns");
      }
    }
  }
}