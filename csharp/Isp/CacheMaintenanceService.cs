using System;
using System.Collections.Generic;

namespace Isp
{
    /// <summary>Клієнт 2: залежить лише від ролей «зміна» та «очищення».</summary>
    public class CacheMaintenanceService<T>
    {
        private readonly IWriteRepository<T> _writer;
        private readonly IPurgeable _purgeable;

        public CacheMaintenanceService(IWriteRepository<T> writer, IPurgeable purgeable)
        {
            _writer = writer ?? throw new ArgumentNullException(nameof(writer));
            _purgeable = purgeable ?? throw new ArgumentNullException(nameof(purgeable));
        }

        /// <summary>Повністю замінює вміст кешу свіжими даними.</summary>
        public void Reload(IEnumerable<T> freshEntities)
        {
            if (freshEntities == null) throw new ArgumentNullException(nameof(freshEntities));

            _purgeable.PurgeAll();
            foreach (var entity in freshEntities)
                _writer.Add(entity);
        }
    }
}
