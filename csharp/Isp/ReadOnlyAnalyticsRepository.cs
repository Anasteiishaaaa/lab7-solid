using System;
using System.Collections.Generic;

namespace Isp
{
    /// <summary>Лише читання та експорт. Жодних методів-заглушок: усе, що оголошено, працює.</summary>
    public class ReadOnlyAnalyticsRepository<T> : IReadRepository<T>, ICsvExporter
    {
        public List<T> GetAll() => new List<T>();
        public T GetById(int id) => default;

        public void ExportToCsv(string path) => Console.WriteLine($"Exported analytics to {path}");
    }
}
