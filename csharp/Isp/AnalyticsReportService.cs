using System;

namespace Isp
{
    /// <summary>
    /// Клієнт 1: залежить лише від ролей «читання» та «експорт у CSV».
    /// Йому не потрібні (і він не бачить) Add/Update/Delete/PurgeAll.
    /// </summary>
    public class AnalyticsReportService<T>
    {
        private readonly IReadRepository<T> _reader;
        private readonly ICsvExporter _exporter;

        public AnalyticsReportService(IReadRepository<T> reader, ICsvExporter exporter)
        {
            _reader = reader ?? throw new ArgumentNullException(nameof(reader));
            _exporter = exporter ?? throw new ArgumentNullException(nameof(exporter));
        }

        /// <returns>Кількість записів у звіті.</returns>
        public int Publish(string path)
        {
            if (string.IsNullOrWhiteSpace(path))
                throw new ArgumentException("Шлях експорту не може бути порожнім.", nameof(path));

            int count = _reader.GetAll().Count;
            _exporter.ExportToCsv(path);
            return count;
        }
    }
}
