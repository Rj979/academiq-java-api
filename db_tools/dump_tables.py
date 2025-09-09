import argparse
import os
from typing import List

import mysql.connector


def list_tables(cursor) -> List[str]:
    cursor.execute("SHOW TABLES")
    return [row[0] for row in cursor.fetchall()]


def dump_table(cursor, table: str, limit: int) -> None:
    cursor.execute(f"SELECT * FROM `{table}` LIMIT %s", (limit,))
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    print(f"\n=== {table} ({len(rows)} rows, showing up to {limit}) ===")
    print(", ".join(columns))
    for row in rows:
        print(row)


def main():
    parser = argparse.ArgumentParser(description="Dump first N rows from all tables in a MySQL database")
    parser.add_argument("--host", default=os.getenv("DB_HOST", "localhost"))
    parser.add_argument("--port", type=int, default=int(os.getenv("DB_PORT", "3306")))
    parser.add_argument("--user", default=os.getenv("DB_USER", "root"))
    parser.add_argument("--password", default=os.getenv("DB_PASSWORD", ""))
    parser.add_argument("--database", default=os.getenv("DB_NAME", "test"))
    parser.add_argument("--limit", type=int, default=50, help="Max rows per table to print")
    args = parser.parse_args()

    conn = mysql.connector.connect(
        host=args.host,
        port=args.port,
        user=args.user,
        password=args.password,
        database=args.database,
    )
    try:
        cursor = conn.cursor()
        tables = list_tables(cursor)
        print(f"Found {len(tables)} tables: {tables}")
        for t in tables:
            try:
                dump_table(cursor, t, args.limit)
            except Exception as table_err:
                print(f"Error dumping {t}: {table_err}")
    finally:
        conn.close()


if __name__ == "__main__":
    main()


