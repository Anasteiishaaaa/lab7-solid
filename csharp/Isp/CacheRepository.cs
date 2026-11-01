using System;
using System.Collections.Generic;

namespace Isp
{
    /// <summary>Читання, зміна й очищення. Не вміє експортувати, і тепер цього й не обіцяє.</summary>
    public class CacheRepository<T> : IReadRepository<T>, IWriteRepository<T>, IPurgeable
    {
        public List<T> GetAll() => new List<T>();
        public T GetById(int id) => default;

        public void Add(T entity) => Console.WriteLine("Added to cache.");
        public void Update(T entity) => Console.WriteLine("Updated in cache.");
        public void Delete(int id) => Console.WriteLine("Removed from cache.");

        public void PurgeAll() => Console.WriteLine("Cache purged.");
    }
}
