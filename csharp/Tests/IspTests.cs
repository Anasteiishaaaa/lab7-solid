using System;
using System.Collections.Generic;
using Isp;
using Xunit;

namespace Tests
{
    public class IspTests
    {
        private sealed class SpyReader : IReadRepository<int>
        {
            public List<int> GetAll() => new List<int> { 1, 2, 3 };
            public int GetById(int id) => id;
        }

        private sealed class SpyExporter : ICsvExporter
        {
            public string Path;
            public void ExportToCsv(string path) => Path = path;
        }

        private sealed class SpyWriter : IWriteRepository<string>
        {
            public List<string> Calls = new List<string>();
            public void Add(string entity) => Calls.Add("add:" + entity);
            public void Update(string entity) => Calls.Add("update:" + entity);
            public void Delete(int id) => Calls.Add("delete:" + id);
        }

        private sealed class SpyPurge : IPurgeable
        {
            public List<string> Calls;
            public SpyPurge(List<string> calls) { Calls = calls; }
            public void PurgeAll() => Calls.Add("purge");
        }

        // Клієнт приймає лише ролі, які використовує: фейки на 1–2 методи, без заглушок.
        [Fact]
        public void ReportService_UsesOnlyReadAndExportRoles()
        {
            var exporter = new SpyExporter();
            var service = new AnalyticsReportService<int>(new SpyReader(), exporter);

            int count = service.Publish("out.csv");

            Assert.Equal(3, count);
            Assert.Equal("out.csv", exporter.Path);
        }

        [Fact]
        public void ReportService_RejectsBadArguments()
        {
            Assert.Throws<ArgumentNullException>(() => new AnalyticsReportService<int>(null, new SpyExporter()));
            Assert.Throws<ArgumentNullException>(() => new AnalyticsReportService<int>(new SpyReader(), null));
            var service = new AnalyticsReportService<int>(new SpyReader(), new SpyExporter());
            Assert.Throws<ArgumentException>(() => service.Publish(" "));
        }

        [Fact]
        public void MaintenanceService_PurgesBeforeAddingFreshData()
        {
            var writer = new SpyWriter();
            var purge = new SpyPurge(writer.Calls);
            new CacheMaintenanceService<string>(writer, purge).Reload(new[] { "a", "b" });

            Assert.Equal(new[] { "purge", "add:a", "add:b" }, writer.Calls);
        }

        [Fact]
        public void MaintenanceService_RejectsNulls()
        {
            var writer = new SpyWriter();
            Assert.Throws<ArgumentNullException>(() => new CacheMaintenanceService<string>(null, new SpyPurge(writer.Calls)));
            Assert.Throws<ArgumentNullException>(() => new CacheMaintenanceService<string>(writer, null));
            Assert.Throws<ArgumentNullException>(() => new CacheMaintenanceService<string>(writer, new SpyPurge(writer.Calls)).Reload(null));
        }

        // Ключова перевірка ISP: клас не обіцяє того, чого не вміє.
        [Fact]
        public void ReadOnlyRepository_DoesNotExposeWriteOrPurgeRoles()
        {
            object repo = new ReadOnlyAnalyticsRepository<int>();
            Assert.True(repo is IReadRepository<int>);
            Assert.True(repo is ICsvExporter);
            Assert.False(repo is IWriteRepository<int>);
            Assert.False(repo is IPurgeable);
        }

        [Fact]
        public void CacheRepository_DoesNotExposeCsvExport()
        {
            object repo = new CacheRepository<int>();
            Assert.True(repo is IReadRepository<int>);
            Assert.True(repo is IWriteRepository<int>);
            Assert.True(repo is IPurgeable);
            Assert.False(repo is ICsvExporter);
        }

        // Усі методи, що залишилися, реально працюють (жодного NotImplementedException).
        [Fact]
        public void ImplementedMethodsDoNotThrow()
        {
            var ro = new ReadOnlyAnalyticsRepository<int>();
            Assert.Equal(0, ro.GetAll().Count);
            ro.ExportToCsv("x.csv");

            var cache = new CacheRepository<int>();
            cache.Add(1); cache.Update(1); cache.Delete(1); cache.PurgeAll();
            Assert.Equal(0, cache.GetAll().Count);
        }
    }
}
